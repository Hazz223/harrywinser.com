import React, { Component } from "react";

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
        Hello dave!!
      </div>
    );
  }
}
export default App;