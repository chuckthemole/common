const React = require('react');
import UserIcon from './user_icon';
import SignupModal from './signup_modal';
import LoginModal from './login_modal';
import Logout from './logout';

/**
 * 
 * @param {*} param0 
 * @returns 
 */
export default function Component({react_component}) {

    // const [component, setComponent] = React.useState(null);

    console.log('component name: ' + react_component);

    if(react_component === 'SignupModal') {
        console.log('setting component to signup modal');
        // setComponent(<SignupModal />);
        return (<SignupModal />);
    } else if(react_component === 'LoginModal') {
        console.log('setting component to login modal');
        // setComponent(<LoginModal />);
        return (<LoginModal />);
    } else if(react_component === 'Logout') {
        console.log('setting component to logout');
        // setComponent(<Logout />);
        return (<Logout />);
    } else if(react_component === 'UserIcon') {
        console.log('setting component to user icon');
        // setComponent(<UserIcon />);
        return (<UserIcon />);
    }

    // return (<>{component}</>)
}