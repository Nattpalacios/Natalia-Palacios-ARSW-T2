var apiclient = (function(){

    function countryByName(name){
        document.getElementById('country').innerHTML = name;
        axios({
            method :'get',
            url : "/coronavirus/"+ name
        })
        .then(response => app.tablaByName(response.data.data.covid19Stats))
        .catch(error => console.log(error));
    }

    function countries(){
        axios({
            method:'get',
            url: "/coronavirus"
        })
        .then(response => app.crearTabla(response.data))
        .catch(error => console.log(error));
    }

    return{
        countries:countries,
        countryByName:countryByName
    }

})();