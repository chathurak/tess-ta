import axios from 'axios'

class RestService {

  getRestClient() {
    if (!this.serviceInstance) {
      this.serviceInstance = axios.create({
        baseURL: '/api',
        timeout: 10000,
        headers: {
          'Content-Type': 'application/json'
        },
      })
    }
    return this.serviceInstance
  }

}

export default (new RestService())
