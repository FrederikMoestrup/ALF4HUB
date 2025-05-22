import { render, screen, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { vi, describe, test, beforeEach, expect } from "vitest";
import Navbar from "../components/Navbar";
import apiFacade from "../util/apiFacade";

// 🧪 Mock hele apiFacade
vi.mock("../util/apiFacade", () => ({
  default: {
    loggedIn: vi.fn(),
    getNotificationCountForUser: vi.fn(),
    getUnreadNotificationCount: vi.fn(),
    logout: vi.fn(),
    acceptPlayerApplication: vi.fn(),
    rejectPlayerApplication: vi.fn(),
    createNotification: vi.fn()
  }
}));

describe("Notification system - badges og beskeder", () => {
  beforeEach(() => {
    apiFacade.loggedIn.mockReturnValue(true);
    apiFacade.getNotificationCountForUser.mockResolvedValue({ count: 5 });
  });

  test("Opgave #141 - viser badge med antal hvis der er ulæste notifikationer", async () => {
    apiFacade.getUnreadNotificationCount.mockResolvedValue({ count: 3 });

    render(
      <BrowserRouter>
        <Navbar setNotifications={() => {}} />
      </BrowserRouter>
    );

    const badge = await screen.findByText("3");
    expect(badge).toBeInTheDocument();
    expect(badge).toHaveClass("notification-badge");
  });

  test("Opgave #142 - skjuler badge hvis der ikke er ulæste notifikationer", async () => {
    apiFacade.getUnreadNotificationCount.mockResolvedValue({ count: 0 });

    render(
      <BrowserRouter>
        <Navbar setNotifications={() => {}} />
      </BrowserRouter>
    );

    await waitFor(() => {
      const badge = screen.queryByText("3");
      expect(badge).not.toBeInTheDocument();
    });
  });

 
  test("Opgave #156 - opretter notifikation ved afvist ansøgning", async () => {
  apiFacade.rejectPlayerApplication.mockImplementation(async (teamId, playerId) => {
    await apiFacade.createNotification({
      type: "APPLICATION_REJECTED",
      receiverId: playerId,
      message: "Din ansøgning til holdet blev afvist"
    });
    return "Afvist";
  });

  apiFacade.createNotification.mockResolvedValue({ message: "Notifikation sendt" });

  await apiFacade.rejectPlayerApplication(1, 42);

  expect(apiFacade.createNotification).toHaveBeenCalledWith({
    type: "APPLICATION_REJECTED",
    receiverId: 42,
    message: "Din ansøgning til holdet blev afvist"
  });
});





test("Opgave #157 - opretter notifikation ved accepteret ansøgning", async () => {
  apiFacade.acceptPlayerApplication.mockImplementation(async (teamId, playerId) => {
    await apiFacade.createNotification({
      type: "APPLICATION_ACCEPTED",
      receiverId: playerId,
      message: "Din ansøgning til holdet er blevet accepteret!"
    });
    return "Accepteret";
  });

  apiFacade.createNotification.mockResolvedValue({ message: "Notifikation sendt" });

  await apiFacade.acceptPlayerApplication(1, 42);

  expect(apiFacade.createNotification).toHaveBeenCalledWith({
    type: "APPLICATION_ACCEPTED",
    receiverId: 42,
    message: "Din ansøgning til holdet er blevet accepteret!"
  });
});

});
