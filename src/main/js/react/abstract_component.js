const React = require('react');
import UserIcon from './user_icon';
import SignupModal from './signup_modal';
import LoginModal from './login_modal';
import Logout from './logout';
import Admin from './admin';

/**
 * 
 * @param {*} param0 
 * @returns 
 */
export default function Component({react_component}) {

    if(react_component === 'SignupModal') {
        return (<SignupModal />);
    } else if(react_component === 'LoginModal') {
        return (<LoginModal />);
    } else if(react_component === 'Logout') {
        return (<Logout />);
    } else if(react_component === 'UserIcon') {
        return (<UserIcon />);
    } else if(react_component === 'Admin') {
        return (<Admin />);
    } else {
        console.log('No component match found for ' + react_component);
    }
}