const baseUrl = 'http://localhost:8080/api'

document.getElementById('login-button').addEventListener('login', function() {
    const data = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    }
    fetch(baseUrl+'/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            window.location.href = 'http://localhost:8080/api/home.html'
        } else {
            return response.json().then(err => {
                throw new Error(err.message);
            })
        }
    })
    .catch((error) => {
        let errorElement = document.getElementById('error-message')
        errorElement.textContent = error.message
        errorElement.style.display = 'flex'
    })
})