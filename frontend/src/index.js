import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Article from './Components/articleComponents/Article';
import AboutMe from './Components/AboutMe';
import Experience from './Components/Experience';
import Projects from "./Components/projectComponents/Projects";
import Marticles from "./Components/articleComponents/Marticles";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import NewArticles from "./Components/homeComponents/NewArticles";
import Basic from "./Basic";
import Articles from "./Components/articleComponents/Articles";
import WriteArticle from "./Components/articleComponents/WriteArticle";
import EditArticle from "./Components/articleComponents/EditArticle";

const sections = [
    {title: 'Articles', url:'/getarticles'},
    {title: 'Project', url:'/project'},
    {title: 'AboutMe', url:'/aboutme'},
    {title: 'Experience', url:'/experience'},
];


const rootElement = document.getElementById("enter");
ReactDOM.render(
    <Router>
        <Routes>
            <Route path="/" element={<Basic sections={sections} title="Myblog"/>}>
                <Route path={""} element={<NewArticles/>}></Route>
                {/*<Route path={"login"} element={<SignIn/>}></Route>*/}
                <Route path="homepage" element={<NewArticles/>}></Route>
                <Route path="getarticles" element={<Articles/>}></Route>
                <Route path="getarticle/:articleid" element={<Article />}></Route>
                <Route path={"getmodulearticles/:moduleid"} element={<Marticles/>}></Route>
                <Route path="project" element={<Projects/>}></Route>
                <Route path="aboutme" element={<AboutMe/>}></Route>
                <Route path="experience" element={<Experience/>}></Route>
                <Route path={"writearticle"} element={<WriteArticle/>}></Route>
                <Route path={"editarticle/:articleid"} element={<EditArticle/>}></Route>
            </Route>
        </Routes>
    </Router>,
    rootElement
);

