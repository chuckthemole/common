import React, { useState } from 'react';
import Modal from 'react-modal';
import { EMPTY } from './common';
import { isModalActive, modal_style, setModalActive, setModalInactive } from './modal_manager';
import { isCurrentUserAuthenticated } from './common_requests';

export default function LoginModal() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const is_user_authenticated = isCurrentUserAuthenticated();

    const [modalIsOpen, setIsOpen] = React.useState(false);

    function openModal() {
        if(!isModalActive()) {
            setIsOpen(true);
            setModalActive();
        }
    }

    function afterOpenModal() {
        // references are now sync'd and can be accessed.
        // subtitle.style.color = '#f00';
    }

    function closeModal() {
        setIsOpen(false);
        setModalInactive();
    }

    function clearInput() {
        setUsername(EMPTY);
        setPassword(EMPTY);
    }

    if(is_user_authenticated.isLoading) {
        console.log('LOADING LOGIN BUTTON');
        return ( // TODO: think about a progress wheel here. maybe return loading value to signal to header - chuck
            <>
                <progress className="progress is-small is-primary" max="100">15%</progress>
            </>
        );
    } else if(is_user_authenticated.isAuthenticated) {
        return (<></>);
    } else {
        return (
            <>
                <a onClick={openModal} className="loginBtn button is-light">
                    Login
                </a>

                <Modal
                    isOpen={modalIsOpen}
                    onAfterOpen={afterOpenModal}
                    onRequestClose={closeModal}
                    className='modal-content'
                    style={modal_style}
                    contentLabel="Example Modal"
                >
                    <div className="modal-content">
                        <form action="/login" method="post" className='box'>
                            <div className="field">
                                <label htmlFor="" className="label">Username</label>
                                <div className="control has-icons-left">
                                    <input name='username' type="username" placeholder="e.g. coolguy" className="input" value={username} onChange={e => setUsername(e.target.value)} required />
                                    <span className="icon is-small is-left">
                                        <i className="fa fa-envelope"></i>
                                    </span>
                                </div>
                            </div>
                            <div className="field">
                                <label htmlFor="" className="label">Password</label>
                                <div className="control has-icons-left">
                                    <input name='password' type="password" placeholder="*******" className="input" value={password} onChange={e => setPassword(e.target.value)} required />
                                    <span className="icon is-small is-left">
                                        <i className="fa fa-lock"></i>
                                    </span>
                                </div>
                            </div>
                            <div className="field">
                                <button id="loginSubmit" type="submit" value="Login" className="button is-success">
                                    Submit
                                </button>
                            </div>
                        </form>
                    </div>
                </Modal>
            </>
        );
    }
}