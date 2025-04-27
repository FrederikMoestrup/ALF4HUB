import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter,createRoutesFromElements, Route, RouterProvider,} from "react-router-dom";
import TeamDashBoard from "./pages/TeamDashBoard";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<TeamDashBoard />}> </Route>
  )
);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);