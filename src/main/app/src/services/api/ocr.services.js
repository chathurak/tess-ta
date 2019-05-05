import restService from './rest.service'

export const uploadFile = (data) => {
  return restService.getRestClient().post('/upload/post', data)
}
