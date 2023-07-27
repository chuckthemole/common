const React = require('react');
import { Link } from 'react-router-dom';
import UserIcon from './user_icon';
import SignupModal from './signup_modal';
import LoginModal from './login_modal';
import Logout from './logout';

export default function Header({user_path, current_user_authorities, is_current_user_authenticated, create_path}) {

    // buttons and icons, empty by default.
    let user_icon = '';
    let admin = '';
    let signout = '';
    let login = '';
    let signup = '';

    // set button and icon visibility depending on user's authentication status and/or state (ie logged in or logged out)
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
   
    return (
        <nav className="navbar" role="navigation" aria-label="main navigation">
            <div className="navbar-brand">
                <Link to={`/`} className="navbar-item">
                    <img src="https://bulma.io/images/bulma-logo.png" width="112" height="28" />
                </Link>
        
                <a role="button" className="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
                    <span aria-hidden="true"></span>
                    <span aria-hidden="true"></span>
                    <span aria-hidden="true"></span>
                </a>
            </div>
        
            <div id="navbarBasicExample" className="navbar-menu">
                <div className="navbar-start">
                    <Link to={`/`} className="navbar-item">
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
                    </div>
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