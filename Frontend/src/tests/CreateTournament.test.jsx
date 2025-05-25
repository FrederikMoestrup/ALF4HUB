import { render, screen, act } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { describe, it, expect, vi } from "vitest";
import "@testing-library/jest-dom";
import CreateTournament from "../pages/tournament/CreateTournament";
import { MemoryRouter } from "react-router-dom";


vi.mock("../pages/tournament/createTournament_apiFacade", () => ({
  default: {
    createTournament: vi.fn().mockResolvedValue({ success: true }),
  },
}));


const renderWithRouter = async (ui) => {
  await act(async () => {
    render(<MemoryRouter>{ui}</MemoryRouter>);
  });
};

describe("CreateTournament - BDD style", () => {
  it("Givet at brugeren udfylder alle felter korrekt, oprettes turneringen", async () => {
    const user = userEvent.setup();
    await renderWithRouter(<CreateTournament />);

    await user.type(
      screen.getByPlaceholderText(/choose name for tournament/i),
      "FIFA Cup 2025"
    );

    const dateInputs = document.querySelectorAll('input[type="date"]');
    await user.clear(dateInputs[0]);
    await user.type(dateInputs[0], "2025-06-01");
    await user.clear(dateInputs[1]);
    await user.type(dateInputs[1], "2025-06-10");

    await user.click(screen.getByRole("button", { name: /confirm/i }));

    expect(screen.queryByText(/please fill in all fields/i)).toBeNull();
  });

  it("Givet at slutdato er før startdato, vises en fejlbesked", async () => {
    const user = userEvent.setup();
    await renderWithRouter(<CreateTournament />);

    await user.type(
      screen.getByPlaceholderText(/choose name for tournament/i),
      "Fejldato Cup"
    );

    const dateInputs = document.querySelectorAll('input[type="date"]');
    await user.clear(dateInputs[0]);
    await user.type(dateInputs[0], "2025-06-10");
    await user.clear(dateInputs[1]);
    await user.type(dateInputs[1], "2025-06-01");

    await user.click(screen.getByRole("button", { name: /confirm/i }));

    expect(
      await screen.findByText("Start date must be before end date.")
    ).toBeInTheDocument();
  });

  it("Givet at startdato er i fortiden, vises en fejlbesked", async () => {
    const user = userEvent.setup();
    await renderWithRouter(<CreateTournament />);

    await user.type(
      screen.getByPlaceholderText(/choose name for tournament/i),
      "Historisk Cup"
    );

    const dateInputs = document.querySelectorAll('input[type="date"]');
    await user.clear(dateInputs[0]);
    await user.type(dateInputs[0], "2000-01-01");
    await user.clear(dateInputs[1]);
    await user.type(dateInputs[1], "2025-06-10");

    await user.click(screen.getByRole("button", { name: /confirm/i }));

    expect(
      await screen.findByText("Start date must be today or in the future.")
    ).toBeInTheDocument();
  });
});
