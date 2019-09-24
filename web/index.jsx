import ReactDOM from "react-dom";
import React from "react";
import App from "./App.jsx";
import '../web/style.css';

const wrapper = document.getElementById("app");
wrapper ? ReactDOM.render(<App />, wrapper) : false;