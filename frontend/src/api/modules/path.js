import ApiService from '@/api'

const BASE_URL = '/paths'

const PathService = {
  get(sourceId, targetId) {
    return ApiService.get(`${BASE_URL}?source=${sourceId}&target=${targetId}&type=DISTANCE`)
  }
}

export default PathService
