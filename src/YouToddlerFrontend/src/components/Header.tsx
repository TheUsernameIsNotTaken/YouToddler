import React from "react";
import {NavLink} from 'react-router-dom'
import App from "../App";

export const Header = () => {

    return(
        <nav>
            <div>
                <div>YouToddler</div>
                <div>
                    <NavLink to=''></NavLink>
                    <NavLink to=''></NavLink>
                </div>
            </div>
        </nav>
    )

}