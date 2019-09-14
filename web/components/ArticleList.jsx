import React, { Component } from "react";
import Article from "./Article.jsx"

class ArticleList extends Component {
 
    constructor(props) {
        super(props);
    }

    render () {
        return (
            <div className="row">
                <div className="col-md-12">
                    I'll contain the articles!
                    <Article/>
                </div>
            </div>
        )
    }
}

export default ArticleList;