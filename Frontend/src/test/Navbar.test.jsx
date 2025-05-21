import { render, screen, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { vi, describe, test, beforeEach, expect } from "vitest";
import Navbar from "../components/Navbar";
import apiFacade from "../components/notifications_apiFacade";


// 🧪 Mock hele notifications_apiFacade så vi kan spionere på metoderne
vi.mock("../components/notifications_apiFacade", () => ({
  default: {
    loggedIn: vi.fn(),
    getNotificationCountForUser: vi.fn(),
    getUnreadNotificationCount: vi.fn(),
    logout: vi.fn(),
  }
}));


describe("Navbar - notification badge behavior", () => {
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
});
