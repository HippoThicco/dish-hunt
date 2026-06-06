import axios from 'axios'

const api = axios.create({ baseURL: import.meta.env.VITE_API_URL ?? '/api' })

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

// Auth
export const register = data => api.post('/auth/register', data)
export const login    = data => api.post('/auth/login', data)

// Recipes
export const getAllRecipes    = (dish) => api.get('/recipes', { params: dish ? { dish } : {} })
export const getRecipe       = (id)   => api.get(`/recipes/${id}`)
export const createRecipe    = (data) => api.post('/recipes', data)

// Users
export const getMe           = ()     => api.get('/users/me')
export const getUser         = (id)   => api.get(`/users/${id}`)
export const getUserRecipes  = (id)   => api.get(`/users/${id}/recipes`)
export const getMyFavourites = ()     => api.get('/users/me/favourites')

// Comments
export const getComments     = (recipeId)        => api.get(`/recipes/${recipeId}/comments`)
export const addComment      = (recipeId, data)  => api.post(`/recipes/${recipeId}/comments`, data)

// Likes
export const toggleLike      = (recipeId) => api.post(`/recipes/${recipeId}/like`)

// Favourites
export const toggleFavourite = (recipeId) => api.post(`/recipes/${recipeId}/favourite`)

export default api
