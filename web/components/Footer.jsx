import React, { Component } from "react";
import Article from "./Article.jsx"

class Footer extends Component {
 
    constructor(props) {
        super(props);
    }

    render () {
        return (
            <div className="row">
                <div className="col-md-12">
                    I'm a footer
                </div>
            </div>
        )
    }
}

export default Footer;