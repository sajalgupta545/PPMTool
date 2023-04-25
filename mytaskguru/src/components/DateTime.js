import React, { useState , useEffect } from 'react';
import { Typography } from '@material-ui/core';

const DateTime = () => {

    var [date,setDate] = useState(new Date());
    
    useEffect(() => {
        var timer = setInterval(()=>setDate(new Date()), 1000 )
        return function cleanup() {
            clearInterval(timer)
        }
    
    });

    return(
        <div>
            <Typography variant="h5"> Time : {date.toLocaleTimeString()}</Typography>
            <Typography variant="h5"> Date : {date.toLocaleDateString()}</Typography>
        </div>
    )
}

export default DateTime;
