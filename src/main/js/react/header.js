const React = require('react');
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faRadiation } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import UserIcon from './user_icon';
import SignupModal from './signup_modal';
import LoginModal from './login_modal';
import Logout from './logout';
import useSWR from 'swr';

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
export default function Header({user_path, current_user_authorities, is_current_user_authenticated, create_path, header_logo}) {

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

    let navbar_brand = '';
    const missing_navbar_brand = navbar_brand = <Link to={`/`} className="navbar-item"><FontAwesomeIcon icon={faRadiation} color='red' /></Link>;
    let navbar_items_start = [];

    if(data !== undefined && data !== null && data !== '') {

        // Navbar brand
        if(data.navbarBrand !== undefined && data.navbarBrand !== null && data.navbarBrand !== '') {
            if(data.navbarBrand.itemType === 'BRAND') {
                navbar_brand = <Link to={data.navbarBrand.href} className="navbar-item"><img src={data.navbarBrand.image} width="112" height="28" /></Link>;
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
                    console.log('data.navbarItemsStart[i].itemType is LINK');
                    console.log(data.navbarItemsStart[i]);
                    navbar_items_start.push(
                        <Link
                            key={data.navbarItemsStart[i].name}
                            to={data.navbarItemsStart[i].href}
                            className="navbar-item">
                                {data.navbarItemsStart[i].name}
                        </Link>
                    );
                } else if(data.navbarItemsStart[i].itemType === 'DROPDOWN') {
                    console.log('data.navbarItemsStart[i].itemType is DROPDOWN');
                    console.log(data.navbarItemsStart[i]);
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
    } else {
        console.log('data is undefined for navbarBrand');
        navbar_brand = missing_navbar_brand;
    }


    // buttons and icons, empty by default.
    let user_icon = '';
    let admin = '';
    let signout = '';
    let login = '';
    let signup = '';

    // set button and icon visibility depending on user's authentication status and/or state (ie logged in or logged out)
    if(is_current_user_authenticated !== undefined && is_current_user_authenticated !== null && is_current_user_authenticated !== '' && is_current_user_authenticated !== false) { // only do if curretn user is authenticated otherwise leave empty
        let is_user_authenticated = is_current_user_authenticated;
        let authorities = current_user_authorities;
        if(!is_user_authenticated.isAuthenticated && !is_user_authenticated.isLoading) {
            login = <LoginModal />;
            signup = <SignupModal create_user_path={create_path}/>;
        } else if(is_user_authenticated.isAuthenticated) {
            user_icon = <UserIcon current_user_path={user_path}/>;
            if(authorities.includes('ROLE_ADMIN')) {
                admin = <Link to={`/admin`} className="adminBtn button is-info"><strong>Admin</strong></Link>;
            }
            signout = <Logout />;
        }
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
                    {/* <Link to={`/`} className="navbar-item">
                        Home
                    </Link>
        
                    <a className="navbar-item">
                        Documentation
                    </a>

                    <div className="navbar-item has-dropdown is-hoverable">
                        <a className="navbar-link">
                            More
                        </a>
        
                        <div className="navbar-dropdown">
                            <a className="navbar-item">
                                About
                            </a>
                            <a className="navbar-item">
                                Jobs
                            </a>
                            <a className="navbar-item">
                                Contact
                            </a>
                            <hr className="navbar-divider" />
                            <a className="navbar-item">
                                Report an issue
                            </a>
                        </div>
                    </div> */}
                </div>
        
                <div className="navbar-end">
                    {user_icon}
                    <div className="navbar-item">
                        <div className="buttons">
                            {admin}
                            {signup}
                            {login}
                            {signout}
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    )
}