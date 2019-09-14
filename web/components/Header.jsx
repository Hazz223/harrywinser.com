import React, { Component } from "react";

class Header extends Component {
 
    constructor(props) {
        super(props);
    }

    render () {
        return (
            <div className="row">
                <div className="col-md-12">
                    I'm a header
                </div>
            </div>
        )
    }
}

export default Header;