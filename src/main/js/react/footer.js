const React = require('react');
import useSWR from 'swr';
import { common_fetcher } from './common_requests';

export default function Footer({footer_path}) {
    const { data, error } = useSWR(
        footer_path,
        common_fetcher
    );

    if (error) {
        console.log(error);
        return(
            <div className='columns is-centered has-text-centered'>
                <div className='column is-half notification is-warning'>
                    <p>An error occurred with footer</p>
                </div>
            </div>
        )
    }

    if (!data) return(
        <div className='container m-6'>
            <progress className="progress is-small is-primary" max="100">15%</progress>
            <progress className="progress is-danger" max="100">30%</progress>
            <progress className="progress is-medium is-dark" max="100">45%</progress>
            <progress className="progress is-large is-info" max="100">60%</progress>
        </div>
    )
   
    return (
        <div className='columns is-centered has-text-centered'>
            <div className='column is-half'>
                <div className="columns is-centered">
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