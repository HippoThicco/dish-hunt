import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider, useAuth } from './context/AuthContext'
import Navbar from './components/Navbar'
import Login from './pages/Login'
import Register from './pages/Register'
import Home from './pages/Home'
import RecipeDetail from './pages/RecipeDetail'
import Profile from './pages/Profile'
import CreateRecipe from './pages/CreateRecipe'

function PrivateRoute({ children }) {
  const { isAuthenticated } = useAuth()
  return isAuthenticated ? children : <Navigate to="/login" replace />
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <div className="min-h-screen bg-orange-50">
          <Navbar />
          <main className="max-w-5xl mx-auto px-4 py-8">
            <Routes>
              <Route path="/"              element={<Home />} />
              <Route path="/login"         element={<Login />} />
              <Route path="/register"      element={<Register />} />
              <Route path="/recipes/:id"   element={<RecipeDetail />} />
              <Route path="/profile/:id"   element={<Profile />} />
              <Route path="/create"        element={<PrivateRoute><CreateRecipe /></PrivateRoute>} />
            </Routes>
          </main>
        </div>
      </BrowserRouter>
    </AuthProvider>
  )
}
