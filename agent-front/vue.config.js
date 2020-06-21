module.exports = {
       
      devServer: {
        headers: { "Access-Control-Allow-Origin": "*" },
        proxy: "http://localhost:8084/"
      }
    
};