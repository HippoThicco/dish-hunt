import { useState, useEffect } from 'react'
import { getAllRecipes } from '../services/api'
import RecipeCard from '../components/RecipeCard'

export default function Home() {
  const [recipes, setRecipes] = useState([])
  const [search, setSearch] = useState('')
  const [query, setQuery] = useState('')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    setLoading(true)
    getAllRecipes(query)
      .then(r => setRecipes(r.data))
      .catch(() => setRecipes([]))
      .finally(() => setLoading(false))
  }, [query])

  function handleSearch(e) {
    e.preventDefault()
    setQuery(search.trim())
  }

  return (
    <div>
      <div className="flex flex-col items-center mb-8">
        <h2 className="text-3xl font-bold text-gray-800 mb-1">Find a Recipe</h2>
        <p className="text-gray-500 text-sm mb-5">Search by dish name or browse all</p>
        <form onSubmit={handleSearch} className="flex gap-2 w-full max-w-md">
          <input
            value={search}
            onChange={e => setSearch(e.target.value)}
            placeholder="e.g. Ramen, Adobo, Pasta…"
            className="flex-1 border border-gray-200 rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400"
          />
          <button
            type="submit"
            className="bg-orange-500 text-white px-4 py-2 rounded-lg text-sm hover:bg-orange-600 transition"
          >
            Search
          </button>
          {query && (
            <button
              type="button"
              onClick={() => { setSearch(''); setQuery('') }}
              className="text-gray-400 hover:text-gray-600 text-sm px-2"
            >
              ✕
            </button>
          )}
        </form>
      </div>

      {loading ? (
        <div className="text-center text-gray-400 py-16">Loading…</div>
      ) : recipes.length === 0 ? (
        <div className="text-center text-gray-400 py-16">
          {query ? `No recipes found for "${query}"` : 'No recipes yet. Be the first to share one!'}
        </div>
      ) : (
        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
          {recipes.map(r => <RecipeCard key={r.id} recipe={r} />)}
        </div>
      )}
    </div>
  )
}
