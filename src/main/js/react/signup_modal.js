import React, { useState } from 'react';
import Modal from 'react-modal';
import { useFetcher } from 'react-router-dom';
import { USERNAME, PASSWORD, EMAIL, EMPTY, POST } from './common';
import { isModalActive, setModalActive, setModalInactive } from './modal_manager';

export default function SignupModal({btn, create_user_path}) {

    let button;
    if(btn === undefined) { // default to signup for now
        button = <span>Sign up</span>;
    } else {
        button = btn;
    }

    const customStyles = {
        content: {
            // top: '50%',
            // left: '50%',
            // right: 'auto',
            // bottom: 'auto',
            // marginRight: '-50%',
            transform: 'translate(0%, 70%)',
        },
    };

    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const fetcher = useFetcher();
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

    async function handleSubmit(e) {
        e.preventDefault();
        const newUser = {};
        newUser[USERNAME] = username;
        newUser[PASSWORD] = password;
        newUser[EMAIL] = email;
        const fetched = await onCreate(newUser);
        closeModal();
        clearInput();
    }

    async function onCreate(newUser) {
        const requestOptions = {
            method: POST,
            redirect: "follow",
            entity: newUser,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newUser)
        };
        return fetch(create_user_path, requestOptions);
	}

    function clearInput() {
        setEmail(EMPTY);
        setUsername(EMPTY);
        setPassword(EMPTY);
    }

    return (
        <>

            <a onClick={openModal} className="signupBtn button is-light is-success">{button}</a>

            <Modal
                isOpen={modalIsOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                className='modal-content'
                style={customStyles}
                contentLabel="Example Modal"
            >
                <div className="modal-content">
                    <fetcher.Form reloadDocument method='post' onSubmit={handleSubmit} className="box">
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
                            <label htmlFor="" className="label">Email</label>
                            <div className="control has-icons-left">
                                <input name='email' type="email" placeholder="e.g. bobsmith@gmail.com" className="input" value={email} onChange={e => setEmail(e.target.value)} required />
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
                        {/* <!-- <div className="field">
                            <label htmlFor="" className="label">Confirm Password</label>
                            <div className="control has-icons-left">
                                <input type="confirm_password" placeholder="*******" className="input" required>
                                <span className="icon is-small is-left">
                                    <i className="fa fa-lock"></i>
                                </span>
                            </div>
                        </div> --> */}
                        <div className="field">
                            <button id="signupSubmit" type="submit" value="Signup" className="button is-success">
                                Submit
                            </button>
                        </div>
                    </fetcher.Form>
                </div>
            </Modal>
        </>
    );
}