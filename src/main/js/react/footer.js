const React = require('react');
import useSWR from 'swr';

// - - - Alternate fetcher, keeping here for now just for reference - - - chuck 2023/7/12

// const fetcher = async url => {
//     const res = await fetch(url)

//     // If the status code is not in the range 200-299,
//     // we still try to parse and throw it.
//     if (!res.ok) {
//         const error = new Error('An error occurred while fetching the data.')
//         // Attach extra info to the error object.
//         error.info = await res.json()
//         error.status = res.status
//         throw error
//     }

//     return res.json()
// }

const fetcher = (...args) => fetch(...args).then((res) => res.json());

export default function Footer({footer_path}) {
    const { data, error } = useSWR(
        footer_path,
        fetcher
    );

    if (error) return <div className='container m-6'><div className='notification is-primary'><p>An error occurred</p></div></div>;

    if (!data) return(
        <div className='container m-6'>
            <progress className="progress is-small is-primary" max="100">15%</progress>
            <progress className="progress is-danger" max="100">30%</progress>
            <progress className="progress is-medium is-dark" max="100">45%</progress>
            <progress className="progress is-large is-info" max="100">60%</progress>
        </div>
    )
   
    return (
        <div className='columns is-centered'>
            <div className='column is-half'>
                <div className="columns">
                    {data.columns.map(({items, title}) => (
                        <div className="column" key={title}>
                            <div>{title}</div>
                            {items.map(item => 
                                <div key={item}>
                                    <span>{item}</span>
                                </div>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}