import React, { useEffect, useState } from 'react';
import useSWR from 'swr';
import { common_fetcher } from './common_requests';

export default function Section({section_path}) {

    const [htmlContent, setHtmlContent] = useState('');
    const { data, error } = useSWR(
        section_path,
        common_fetcher
    );
    
    useEffect(() => {
        if(data !== undefined) {
            setHtmlContent(constructHtmlContent(data));
        }
    }, [data]);

    if (error) {
        console.log(error);
        return(
            <div className='columns is-centered has-text-centered'>
                <div className='column is-half notification is-warning'>
                    <p>An error occurred getting section with path: {section_path}</p>
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
   
    console.log(data);
    console.log(htmlContent);

    // TODO: data.resources think about what to do...

    // recursive function to construct the html content from parent object
    function constructHtmlContent(currentObject) {
        if(currentObject !== undefined) {
            console.log(currentObject.body);
            console.log(currentObject.children.length);
            // iterate over currentObject.htmlTagAttributes object and add each attribute to the html tag
            let htmlTag = '';
            let htmlTagAttributes = '';
            if(currentObject.htmlTagAttributes !== undefined) {
                for(let key in currentObject.htmlTagAttributes) {
                    htmlTagAttributes += `${key}="${currentObject.htmlTagAttributes[key]}" `;
                }

                // construct the html tag with the attributes and currentObject.htmlTagTypeValue
                htmlTag = `<${currentObject.htmlTagTypeValue} ${htmlTagAttributes}>${currentObject.body}`;
            }

            if(currentObject.children !== undefined && currentObject.children.length > 0) {
                return (`${htmlTag}${currentObject.children.map(child => constructHtmlContent(child)).join('')}</${currentObject.htmlTagTypeValue}>`);
            } else {
                return (`${htmlTag}</${currentObject.htmlTagTypeValue}>`);
            }
        }
        return '';
    }

    console.log(htmlContent);
    
    return (
        <>
            <div dangerouslySetInnerHTML={{__html: htmlContent}}></div>
        </>
    )
}