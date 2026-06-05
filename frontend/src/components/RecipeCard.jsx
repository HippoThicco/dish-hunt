import { useNavigate } from 'react-router-dom'

export default function RecipeCard({ recipe }) {
  const navigate = useNavigate()

  return (
    <div
      onClick={() => navigate(`/recipes/${recipe.id}`)}
      className="bg-white rounded-xl shadow-sm border border-orange-100 p-4 cursor-pointer hover:shadow-md hover:border-orange-300 transition"
    >
      <div className="flex justify-between items-start">
        <div>
          <h3 className="font-semibold text-gray-800 text-lg leading-tight">{recipe.title}</h3>
          {recipe.dishName && (
            <span className="text-xs bg-orange-100 text-orange-700 px-2 py-0.5 rounded-full mt-1 inline-block">
              {recipe.dishName}
              {recipe.dishCuisine ? ` · ${recipe.dishCuisine}` : ''}
            </span>
          )}
        </div>
        <span className="text-orange-400 text-sm font-medium">
          ♥ {recipe.likeCount}
        </span>
      </div>

      {recipe.description && (
        <p className="text-gray-500 text-sm mt-2 line-clamp-2">{recipe.description}</p>
      )}

      <div className="mt-3 flex justify-between items-center text-xs text-gray-400">
        <span>by {recipe.author?.name || recipe.author?.username}</span>
        {recipe.calories && <span>{recipe.calories} kcal</span>}
      </div>
    </div>
  )
}
