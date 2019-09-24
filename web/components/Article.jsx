import React, { Component } from "react";

class Article extends Component {
 
    constructor(props) {
        super(props);
    }

    render () {
        return (
            <div className="row">
                <div className="col-md-12">
                    <h1>{this.props.article.title}</h1>
                    <div dangerouslySetInnerHTML={{ __html: this.props.article.data }}></div>
                </div>
            </div>
        )
    }
}

export default Article;