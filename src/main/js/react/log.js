const React = require('react');
const ReactDOM = require('react-dom/client');

export async function loader(page) {
    // default to sort by username if not defined
    const response = await fetch('/api/logs/' + page );
    // If the status code is not in the range 200-299,
    // we still try to parse and throw it.
    if (!response.ok) {
        const error = new Error('An error occurred while fetching logs');
        // Attach extra info to the error object.
        error.info = await response.json();
        error.status = response.status;
        throw error;
    }
    if(response.redirected == true) { // catching this and returning null as to not get console error
        return null;
    }

    return response.json();
}

export default function Log({page}) {

    const [logs, setLogs] = React.useState([]);

    React.useEffect(() => { // TODO: this calls the api a lot. figure out a resolution.
        loader(page).then((response) => {
            console.log(response);
            setLogs(response);
        });
    }, [logs]);

    if (!logs) return(
        <div className='container m-6'>
            <progress className="progress is-small is-primary" max="100">15%</progress>
            <progress className="progress is-danger" max="100">30%</progress>
            <progress className="progress is-medium is-dark" max="100">45%</progress>
            <progress className="progress is-large is-info" max="100">60%</progress>
        </div>
    )

    return (
        <>
            {/* TODO: work on search filter <div className='m-6'>
                <Dropdown className='columns' title={'Search filter'} dropdown_items={search_filter} />
                <div className='columns'><input className="column is-one-third input" type="text" placeholder={get_selected()}></input></div>
            </div> */}

            <div className='table-container'>
                <table className="table is-hoverable is-fullwidth is-bordered m-6">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>
                                <abbr title="User Name">User</abbr>
                            </th>
                            <th>
                                <abbr title="User Name">ID</abbr>
                            </th>
                            <th>
                                <abbr title="Password">Action</abbr>
                                </th>
                            <th>
                                <abbr title="User Authorizations">Time</abbr>
                            </th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>#</th>
                            <th>
                                <abbr title="User Name">User</abbr>
                            </th>
                            <th>
                                <abbr title="User Name">ID</abbr>
                            </th>
                            <th>
                                <abbr title="Password">Action</abbr>
                                </th>
                            <th>
                                <abbr title="User Authorizations">Time</abbr>
                            </th>
                        </tr>
                    </tfoot>
                    <tbody>
                        {logs.map(( log, index ) => (
                            <tr key={index}>
                                <th>{index + 1}</th>
                                <td>{log.username}</td>
                                <td>{log.userId}</td>
                                <td>{log.action}</td>
                                <td>{log.time}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </>
    )
}