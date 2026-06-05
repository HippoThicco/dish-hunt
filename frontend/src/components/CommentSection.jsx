import { useState, useEffect } from 'react'
import { getComments, addComment } from '../services/api'
import { useAuth } from '../context/AuthContext'

export default function CommentSection({ recipeId }) {
  const [comments, setComments] = useState([])
  const [content, setContent] = useState('')
  const [error, setError] = useState('')
  const { isAuthenticated } = useAuth()

  useEffect(() => {
    getComments(recipeId).then(r => setComments(r.data)).catch(() => {})
  }, [recipeId])

  async function handleSubmit(e) {
    e.preventDefault()
    if (!content.trim()) return
    try {
      const res = await addComment(recipeId, { content })
      setComments(prev => [res.data, ...prev])
      setContent('')
      setError('')
    } catch {
      setError('Failed to post comment.')
    }
  }

  return (
    <div className="mt-8">
      <h3 className="text-lg font-semibold text-gray-800 mb-4">Comments</h3>

      {isAuthenticated && (
        <form onSubmit={handleSubmit} className="mb-6">
          <textarea
            value={content}
            onChange={e => setContent(e.target.value)}
            rows={3}
            placeholder="Write a comment…"
            className="w-full border border-gray-200 rounded-lg p-3 text-sm focus:outline-none focus:ring-2 focus:ring-orange-400 resize-none"
          />
          {error && <p className="text-red-500 text-sm mt-1">{error}</p>}
          <button
            type="submit"
            className="mt-2 bg-orange-500 text-white px-4 py-1.5 rounded-lg text-sm hover:bg-orange-600 transition"
          >
            Post
          </button>
        </form>
      )}

      {comments.length === 0 ? (
        <p className="text-gray-400 text-sm">No comments yet.</p>
      ) : (
        <div className="space-y-4">
          {comments.map(c => (
            <div key={c.id} className="bg-gray-50 rounded-lg p-3">
              <div className="flex justify-between text-xs text-gray-400 mb-1">
                <span className="font-medium text-gray-600">{c.author?.name || c.author?.username}</span>
                <span>{new Date(c.createdAt).toLocaleDateString()}</span>
              </div>
              <p className="text-sm text-gray-700">{c.content}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
