import { useState, useEffect } from 'react'
import { useParams, useNavigate, Link } from 'react-router-dom'
import { getRecipe, toggleLike, toggleFavourite } from '../services/api'
import { useAuth } from '../context/AuthContext'
import CommentSection from '../components/CommentSection'

export default function RecipeDetail() {
  const { id } = useParams()
  const [recipe, setRecipe] = useState(null)
  const [loading, setLoading] = useState(true)
  const { isAuthenticated } = useAuth()
  const navigate = useNavigate()

  useEffect(() => {
    getRecipe(id)
      .then(r => setRecipe(r.data))
      .catch(() => navigate('/'))
      .finally(() => setLoading(false))
  }, [id])

  async function handleLike() {
    if (!isAuthenticated) return navigate('/login')
    const res = await toggleLike(id)
    setRecipe(r => ({ ...r, likedByCurrentUser: res.data.liked, likeCount: res.data.likeCount }))
  }

  async function handleFavourite() {
    if (!isAuthenticated) return navigate('/login')
    const res = await toggleFavourite(id)
    setRecipe(r => ({ ...r, favouritedByCurrentUser: res.data.favourited }))
  }

  if (loading) return <div className="text-center text-gray-400 py-20">Loading…</div>
  if (!recipe) return null

  return (
    <div className="max-w-2xl mx-auto">
      <button onClick={() => navigate(-1)} className="text-gray-400 hover:text-gray-600 text-sm mb-4">
        ← Back
      </button>

      <div className="bg-white rounded-2xl shadow-sm p-8">
        <div className="flex justify-between items-start mb-2">
          <h1 className="text-2xl font-bold text-gray-800">{recipe.title}</h1>
          <div className="flex gap-2">
            <button
              onClick={handleLike}
              title="Like"
              className={`flex items-center gap-1 px-3 py-1.5 rounded-full text-sm font-medium transition ${
                recipe.likedByCurrentUser
                  ? 'bg-red-100 text-red-500'
                  : 'bg-gray-100 text-gray-500 hover:bg-red-50 hover:text-red-400'
              }`}
            >
              ♥ {recipe.likeCount}
            </button>
            <button
              onClick={handleFavourite}
              title="Favourite"
              className={`px-3 py-1.5 rounded-full text-sm font-medium transition ${
                recipe.favouritedByCurrentUser
                  ? 'bg-yellow-100 text-yellow-600'
                  : 'bg-gray-100 text-gray-500 hover:bg-yellow-50 hover:text-yellow-500'
              }`}
            >
              {recipe.favouritedByCurrentUser ? '★ Saved' : '☆ Save'}
            </button>
          </div>
        </div>

        <div className="flex flex-wrap gap-2 mb-4 text-sm text-gray-500">
          <span>
            by{' '}
            <Link to={`/profile/${recipe.author?.id}`} className="text-orange-500 hover:underline">
              {recipe.author?.name || recipe.author?.username}
            </Link>
          </span>
          {recipe.dishName && (
            <span className="bg-orange-100 text-orange-700 px-2 py-0.5 rounded-full text-xs">
              {recipe.dishName}{recipe.dishCuisine ? ` · ${recipe.dishCuisine}` : ''}
            </span>
          )}
          {recipe.calories && <span>{recipe.calories} kcal</span>}
        </div>

        {recipe.description && (
          <p className="text-gray-600 mb-6">{recipe.description}</p>
        )}

        {recipe.ingredients?.length > 0 && (
          <div className="mb-6">
            <h2 className="font-semibold text-gray-800 mb-2">Ingredients</h2>
            <ul className="space-y-1">
              {recipe.ingredients.map((ing, i) => (
                <li key={i} className="text-sm text-gray-600 flex gap-2">
                  <span className="text-orange-400">•</span>
                  <span>
                    {ing.quantity && `${ing.quantity} `}
                    {ing.unit && `${ing.unit} `}
                    {ing.name}
                  </span>
                </li>
              ))}
            </ul>
          </div>
        )}

        <div>
          <h2 className="font-semibold text-gray-800 mb-2">Instructions</h2>
          <p className="text-gray-600 text-sm whitespace-pre-line leading-relaxed">
            {recipe.instructions}
          </p>
        </div>

        <CommentSection recipeId={id} />
      </div>
    </div>
  )
}
