const React = require('react');
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faRadiation } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import UserIcon from './user_icon';
import SignupModal from './signup_modal';
import LoginModal from './login_modal';
import Logout from './logout';
import useSWR from 'swr';
import Component from './abstract_component';

const fetcher = (...args) => fetch(...args).then((res) => res.json());

/**
 * 
 * @param {*} user_path path to user icon
 * @param {*} current_user_authorities authorities of current user
 * @param {*} is_current_user_authenticated is current user authenticated
 * @param {*} create_path path to create user
 * @param {*} header_logo logo path for header
 * 
 * @returns 
 */
export default function Header() {

    const { data, error } = useSWR(
        '/view/header',
        fetcher
    );

    console.log(data);
    if(data !== undefined && data !== null && data !== '') {
        console.log(typeof data);
    }
    if (error) {
        // TODO: add some animation here
        console.log(error);
    }

    if (!data) {
        // TODO: add some animation here
        console.log('loading header');
    }

    // Navbar items
    // TODO: think more about active switch for elements
    let navbar_brand = '';
    const missing_navbar_brand = navbar_brand =
        <Link
            to={`/`}
            className="navbar-item">
                <FontAwesomeIcon icon={faRadiation} color='red' />
        </Link>;
    let navbar_items_start = [];
    let navbar_items_end = [];

    if(data !== undefined && data !== null && data !== '') {

        // Navbar brand
        if(data.navbarBrand !== undefined && data.navbarBrand !== null && data.navbarBrand !== '') {
            if(data.navbarBrand.itemType === 'BRAND') {
                navbar_brand =
                    <Link
                        to={data.navbarBrand.href}
                        className="navbar-item">
                            <img src={data.navbarBrand.image} width="112" height="28" />
                    </Link>;
            } else {
                console.log('data.navbarBrand.itemType is undefined');
                navbar_brand = missing_navbar_brand;
            }
            // TODO add more item types
        } else {
            console.log('data.navbarBrand is undefined');
            navbar_brand = missing_navbar_brand;
        }

        // Navbar beginning
        if(data.navbarItemsStart !== undefined && data.navbarItemsStart !== null && data.navbarItemsStart !== '' && data.navbarItemsStart.length > 0) {
            for(let i = 0; i < data.navbarItemsStart.length; i++) {
                if(data.navbarItemsStart[i].itemType === 'LINK') {
                    navbar_items_start.push(
                        <Link
                            key={data.navbarItemsStart[i].name}
                            to={data.navbarItemsStart[i].href}
                            className="navbar-item">
                                {data.navbarItemsStart[i].name}
                        </Link>
                    );
                } else if(data.navbarItemsStart[i].itemType === 'DROPDOWN') {
                    if(data.navbarItemsStart[i].dropdown !== undefined && data.navbarItemsStart[i].dropdown !== null && data.navbarItemsStart[i].dropdown !== '' && data.navbarItemsStart[i].dropdown.length > 0) {
                        let dropdown_items = [];
                        for(let j = 0; j < data.navbarItemsStart[i].dropdown.length; j++) {
                            if(data.navbarItemsStart[i].dropdown[j].itemType === 'LINK') {
                                dropdown_items.push(
                                    <Link
                                        key={data.navbarItemsStart[i].name + data.navbarItemsStart[i].dropdown[j].name}
                                        to={data.navbarItemsStart[i].dropdown[j].href}
                                        className="navbar-item">
                                            {data.navbarItemsStart[i].dropdown[j].name}
                                    </Link>
                                );
                            } else if(data.navbarItemsStart[i].dropdown[j].itemType === 'DROPDOWN_DIVIDER') {
                                dropdown_items.push(
                                    <hr key={data.navbarItemsStart[i].name + data.navbarItemsStart[i].dropdown[j].name} className="navbar-divider" />
                                );
                            }
                        }
                        navbar_items_start.push(
                            <div key={data.navbarItemsStart[i].name} className="navbar-item has-dropdown is-hoverable">
                                <a className="navbar-link">
                                    {data.navbarItemsStart[i].name}
                                </a>
                                <div className="navbar-dropdown">
                                    {dropdown_items}
                                </div>
                            </div>
                        );

                    }
                }
                // TODO: add more item types
            }
        } else {
            console.log('data.navbarItemsStart is undefined');
        }

        // Navbar end
        if(data.navbarItemsEnd !== undefined && data.navbarItemsEnd !== null && data.navbarItemsEnd !== '' && data.navbarItemsEnd.length > 0) {
            for(let i = 0; i < data.navbarItemsEnd.length; i++) {
                if(data.navbarItemsEnd[i].itemType === 'LINK') {
                    navbar_items_end.push(
                        <Link
                            key={data.navbarItemsEnd[i].name}
                            to={data.navbarItemsEnd[i].href}
                            className="navbar-item">
                                {data.navbarItemsEnd[i].name}
                        </Link>
                    );
                } else if(data.navbarItemsEnd[i].itemType === 'DROPDOWN') {
                    if(data.navbarItemsEnd[i].dropdown !== undefined && data.navbarItemsEnd[i].dropdown !== null && data.navbarItemsEnd[i].dropdown !== '' && data.navbarItemsEnd[i].dropdown.length > 0) {
                        let dropdown_items = [];
                        for(let j = 0; j < data.navbarItemsEnd[i].dropdown.length; j++) {
                            if(data.navbarItemsEnd[i].dropdown[j].itemType === 'LINK') {
                                dropdown_items.push(
                                    <Link
                                        key={data.navbarItemsEnd[i].name + data.navbarItemsEnd[i].dropdown[j].name}
                                        to={data.navbarItemsEnd[i].dropdown[j].href}
                                        className="navbar-item">
                                            {data.navbarItemsEnd[i].dropdown[j].name}
                                    </Link>
                                );
                            }
                        }
                    }
                } else if(data.navbarItemsEnd[i].itemType === 'REACT_COMPONENT') {
                    navbar_items_end.push(
                            <Component
                                react_component={data.navbarItemsEnd[i].reactComponent}
                                key={data.navbarItemsEnd[i].name}
                                className="navbar-item">
                            </Component>
                    );
                }
            }
        } else {
            console.log('data.navbarItemsEnd is undefined');
        }

    } else {
        console.log('data is undefined for navbarBrand');
        navbar_brand = missing_navbar_brand;
    }
   
    return (
        <nav className="navbar" role="navigation" aria-label="main navigation">
            <div className="navbar-brand">
                {navbar_brand}
                <a role="button" className="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
                    <span aria-hidden="true"></span>
                    <span aria-hidden="true"></span>
                    <span aria-hidden="true"></span>
                </a>
            </div>
        
            <div id="navbarBasicExample" className="navbar-menu">
                <div className="navbar-start">
                    {navbar_items_start}
                </div>
        
                <div className="navbar-end">
                    <div className="navbar-item">
                        <div className="buttons">
                            {navbar_items_end}
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    )
}