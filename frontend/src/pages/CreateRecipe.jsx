import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { createRecipe } from '../services/api'

const EMPTY_INGREDIENT = { name: '', quantity: '', unit: '' }

export default function CreateRecipe() {
  const navigate = useNavigate()
  const [form, setForm] = useState({
    title: '',
    description: '',
    calories: '',
    instructions: '',
    dishName: '',
    dishCuisine: '',
  })
  const [ingredients, setIngredients] = useState([{ ...EMPTY_INGREDIENT }])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  function handleChange(e) {
    setForm(f => ({ ...f, [e.target.name]: e.target.value }))
  }

  function handleIngredientChange(i, field, value) {
    setIngredients(prev => prev.map((ing, idx) => idx === i ? { ...ing, [field]: value } : ing))
  }

  function addIngredient() {
    setIngredients(prev => [...prev, { ...EMPTY_INGREDIENT }])
  }

  function removeIngredient(i) {
    setIngredients(prev => prev.filter((_, idx) => idx !== i))
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const payload = {
        ...form,
        calories: form.calories ? Number(form.calories) : null,
        ingredients: ingredients
          .filter(i => i.name.trim())
          .map(i => ({
            name: i.name,
            quantity: i.quantity ? Number(i.quantity) : null,
            unit: i.unit || null,
          }))
      }
      const res = await createRecipe(payload)
      navigate(`/recipes/${res.data.id}`)
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to create recipe')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="max-w-2xl mx-auto">
      <h1 className="text-2xl font-bold text-gray-800 mb-6">Share a Recipe</h1>
      <form onSubmit={handleSubmit} className="bg-white rounded-2xl shadow-sm p-8 space-y-5">
        {error && <p className="bg-red-50 text-red-600 text-sm p-3 rounded-lg">{error}</p>}

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Title *</label>
          <input name="title" value={form.title} onChange={handleChange} required
            className="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Dish Name</label>
            <input name="dishName" value={form.dishName} onChange={handleChange}
              className="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Cuisine</label>
            <input name="dishCuisine" value={form.dishCuisine} onChange={handleChange}
              className="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
          <textarea name="description" value={form.description} onChange={handleChange} rows={2}
            className="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400 resize-none" />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Calories</label>
          <input type="number" name="calories" value={form.calories} onChange={handleChange} min={0}
            className="w-32 border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400" />
        </div>

        <div>
          <div className="flex justify-between items-center mb-2">
            <label className="block text-sm font-medium text-gray-700">Ingredients</label>
            <button type="button" onClick={addIngredient}
              className="text-orange-500 text-sm hover:underline">+ Add</button>
          </div>
          <div className="space-y-2">
            {ingredients.map((ing, i) => (
              <div key={i} className="flex gap-2">
                <input
                  placeholder="Name"
                  value={ing.name}
                  onChange={e => handleIngredientChange(i, 'name', e.target.value)}
                  className="flex-1 border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400"
                />
                <input
                  placeholder="Qty"
                  type="number"
                  min={0}
                  value={ing.quantity}
                  onChange={e => handleIngredientChange(i, 'quantity', e.target.value)}
                  className="w-20 border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400"
                />
                <input
                  placeholder="Unit"
                  value={ing.unit}
                  onChange={e => handleIngredientChange(i, 'unit', e.target.value)}
                  className="w-20 border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400"
                />
                {ingredients.length > 1 && (
                  <button type="button" onClick={() => removeIngredient(i)}
                    className="text-gray-400 hover:text-red-400 text-lg leading-none">×</button>
                )}
              </div>
            ))}
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Instructions *</label>
          <textarea name="instructions" value={form.instructions} onChange={handleChange} required rows={6}
            placeholder="Step 1: …&#10;Step 2: …"
            className="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400 resize-none" />
        </div>

        <div className="flex gap-3">
          <button
            type="submit"
            disabled={loading}
            className="bg-orange-500 text-white px-6 py-2 rounded-lg font-medium hover:bg-orange-600 transition disabled:opacity-50"
          >
            {loading ? 'Posting…' : 'Post Recipe'}
          </button>
          <button
            type="button"
            onClick={() => navigate(-1)}
            className="text-gray-500 px-4 py-2 rounded-lg hover:bg-gray-100 transition"
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  )
}
