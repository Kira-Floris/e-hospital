const axios = require('axios');

const ResponseFormat = (data, status) => {
    return {
        data: data,
        status: status
    };
};

module.exports = ResponseFormat;