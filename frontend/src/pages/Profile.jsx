import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { getUser, getUserRecipes, getMyFavourites } from '../services/api'
import { useAuth } from '../context/AuthContext'
import RecipeCard from '../components/RecipeCard'

export default function Profile() {
  const { id } = useParams()
  const { user: currentUser } = useAuth()
  const [profile, setProfile] = useState(null)
  const [recipes, setRecipes] = useState([])
  const [favourites, setFavourites] = useState([])
  const [tab, setTab] = useState('recipes')
  const [loading, setLoading] = useState(true)

  const isOwnProfile = currentUser && String(currentUser.userId) === String(id)

  useEffect(() => {
    setLoading(true)
    Promise.all([
      getUser(id),
      getUserRecipes(id),
      isOwnProfile ? getMyFavourites() : Promise.resolve({ data: [] })
    ])
      .then(([profileRes, recipesRes, favsRes]) => {
        setProfile(profileRes.data)
        setRecipes(recipesRes.data)
        setFavourites(favsRes.data)
      })
      .finally(() => setLoading(false))
  }, [id, isOwnProfile])

  if (loading) return <div className="text-center text-gray-400 py-20">Loading…</div>
  if (!profile) return null

  return (
    <div className="max-w-2xl mx-auto">
      <div className="bg-white rounded-2xl shadow-sm p-8 mb-6">
        <div className="flex items-center gap-4 mb-4">
          <div className="w-16 h-16 rounded-full bg-orange-100 flex items-center justify-center text-2xl font-bold text-orange-500">
            {(profile.name || profile.username)[0].toUpperCase()}
          </div>
          <div>
            <h1 className="text-xl font-bold text-gray-800">{profile.name}</h1>
            <p className="text-gray-400 text-sm">@{profile.username}</p>
          </div>
        </div>

        <div className="grid grid-cols-3 gap-4 text-center text-sm mb-4">
          <div>
            <p className="font-semibold text-gray-800">{profile.recipeCount}</p>
            <p className="text-gray-400">Recipes</p>
          </div>
          {profile.nationality && (
            <div>
              <p className="font-semibold text-gray-800">{profile.nationality}</p>
              <p className="text-gray-400">From</p>
            </div>
          )}
          <div>
            <p className="font-semibold text-gray-800">
              {profile.joinDate ? new Date(profile.joinDate).getFullYear() : '—'}
            </p>
            <p className="text-gray-400">Joined</p>
          </div>
        </div>

        {profile.bio && <p className="text-gray-600 text-sm">{profile.bio}</p>}
      </div>

      {isOwnProfile && (
        <div className="flex gap-2 mb-4">
          <button
            onClick={() => setTab('recipes')}
            className={`px-4 py-1.5 rounded-full text-sm font-medium transition ${
              tab === 'recipes' ? 'bg-orange-500 text-white' : 'bg-white text-gray-500 hover:bg-orange-50'
            }`}
          >
            My Recipes
          </button>
          <button
            onClick={() => setTab('favourites')}
            className={`px-4 py-1.5 rounded-full text-sm font-medium transition ${
              tab === 'favourites' ? 'bg-orange-500 text-white' : 'bg-white text-gray-500 hover:bg-orange-50'
            }`}
          >
            Favourites
          </button>
        </div>
      )}

      {(() => {
        const list = tab === 'favourites' ? favourites : recipes
        if (list.length === 0) {
          return <p className="text-gray-400 text-sm text-center py-8">Nothing here yet.</p>
        }
        return (
          <div className="grid gap-4 sm:grid-cols-2">
            {list.map(r => <RecipeCard key={r.id} recipe={r} />)}
          </div>
        )
      })()}
    </div>
  )
}
