const React = require('react');
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

export default function RumpusQuill() {

    const [value, setValue] = React.useState('');

    const modules = {
        toolbar: [
            [{ 'header': [1, 2, false] }],
            ['bold', 'italic', 'underline','strike', 'blockquote'],
            [{'list': 'ordered'}, {'list': 'bullet'}, {'indent': '-1'}, {'indent': '+1'}],
            ['link', 'image'],
            ['clean']
        ],
    }
    
    const formats = [
        'header',
        'bold', 'italic', 'underline', 'strike', 'blockquote',
        'list', 'bullet', 'indent',
        'link', 'image'
    ]

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
            placeholder='Compose an epic...'
            modules={modules}
            formats={formats}
        />
    );

    return (<>{quill}</>);
}