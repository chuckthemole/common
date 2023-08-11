const React = require('react');
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

let text_editor_body;

export function getQuillContents() {
    if(text_editor_body !== undefined) {
        return text_editor_body;
    }
    return '';
}

export default function RumpusQuill({modules, formats, placeholder}) {

    const [value, setValue] = React.useState('');

    React.useEffect(() => {
        text_editor_body = value;
    }, [value]);

    const default_placeholder = 'Compose an epic...';

    const default_modules = {
        toolbar: [
            [{ 'header': [1, 2, false] }],
            ['bold', 'italic', 'underline','strike', 'blockquote'],
            [{'list': 'ordered'}, {'list': 'bullet'}, {'indent': '-1'}, {'indent': '+1'}],
            ['link', 'image'],
            ['clean']
        ],
    };
    
    const default_formats = [
        'header',
        'bold', 'italic', 'underline', 'strike', 'blockquote',
        'list', 'bullet', 'indent',
        'link', 'image'
    ];

    let quill = React.createElement(

        // type
        'div',

        // props
        {
            style: {
                background: "white"
            }
        },

        // children
        <ReactQuill
            theme='snow'
            value={value}
            onChange={setValue}
            placeholder={placeholder !== undefined ? placeholder : default_placeholder}
            modules={modules !== undefined ? modules : default_modules}
            formats={formats !== undefined ? formats : default_formats}
        />
    );

    return (<>{quill}</>);
}