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
export default function ReactComponent({component_name}) {

    if(component_name === 'SignupModal') {
        // TODO: this is where Signup is failing. Need to supply a create_user_path
        return (<SignupModal create_user_path={'/api/user'} />);
    } else if(component_name === 'LoginModal') {
        return (<LoginModal />);
    } else if(component_name === 'Logout') {
        return (<Logout />);
    } else if(component_name === 'UserIcon') {
        return (<UserIcon />);
    } else if(component_name === 'Admin') {
        return (<Admin />);
    } else {
        console.log('No component match found for ' + component_name);
    }
}