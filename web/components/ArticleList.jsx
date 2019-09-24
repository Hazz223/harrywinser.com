import React, { Component } from "react";
import SuperAgent from "superagent";
import Article from "./Article.jsx"

class ArticleList extends Component {
 
    constructor(props) {
        super(props);
        this.state = {
            articles: [{title:"Loading"}]
        }
    }

    componentDidMount() {
        SuperAgent.get("/api/articles/")
            .then(payload => {
                console.log(payload.body)
                this.setState({
                    articles:payload.body
                })
            })
            .catch(err => {
                // todo: add a nice error banner or something?
                // This is kinda where state comes into it, isn't it?
                console.log("error occurred getting articles")
            })
    }

    render () {

        var articleList = this.state.articles.map(v =>             
            <Article key={v.title} article={v}  />
        );

        return (
            <div className="row">
                <div className="col-md-12">
                    {articleList}
                </div>
            </div>
        )
    }
}

export default ArticleList;