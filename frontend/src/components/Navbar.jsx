import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Navbar() {
  const { user, isAuthenticated, logout } = useAuth()
  const navigate = useNavigate()

  function handleLogout() {
    logout()
    navigate('/login')
  }

  return (
    <nav className="bg-orange-500 text-white shadow-md">
      <div className="max-w-5xl mx-auto px-4 h-14 flex items-center justify-between">
        <Link to="/" className="text-xl font-bold tracking-tight hover:opacity-90">
          🍽 Dish Hunt
        </Link>
        <div className="flex items-center gap-4 text-sm font-medium">
          {isAuthenticated ? (
            <>
              <Link
                to="/create"
                className="bg-white text-orange-600 px-3 py-1 rounded-full hover:bg-orange-100 transition"
              >
                + New Recipe
              </Link>
              <Link
                to={`/profile/${user.userId}`}
                className="hover:underline"
              >
                {user.username}
              </Link>
              <button onClick={handleLogout} className="hover:underline">
                Logout
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="hover:underline">Login</Link>
              <Link
                to="/register"
                className="bg-white text-orange-600 px-3 py-1 rounded-full hover:bg-orange-100 transition"
              >
                Sign Up
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  )
}
