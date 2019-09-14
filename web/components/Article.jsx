import React, { Component } from "react";

class Article extends Component {
 
    constructor(props) {
        super(props);
    }

    render () {
        return (
            <div className="row">
                <div className="col-md-12">
                    I'm an article
                </div>
            </div>
        )
    }
}

export default Article;