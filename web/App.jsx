import React, { Component } from "react";
import Header from "./components/Header.jsx"
import Navigation from "./components/Navigation.jsx"
import ArticleList from "./components/ArticleList.jsx"
import Footer from "./components/Footer.jsx"

class App extends Component {

  constructor(props) {
    super(props);
    this.handleOnEnvChange = this.handleOnEnvChange.bind(this);
  }

  handleOnEnvChange() {
    console.log("Something happened")
  }

  render() {
    return (
      <div className="container-fluid">
        <Header/>
        <Navigation/>
        <ArticleList/>
        <Footer/>
      </div>
    );
  }
}
export default App;