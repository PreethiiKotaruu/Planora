import './App.css'

function App() {
  return (
    <div className="app">
      <header className="navbar">
        <h2 className="logo">Planora</h2>

        <nav className="nav-links">
          <a href="#">How it works</a>
          <a href="#">Log in</a>
          <button className="nav-button">Get started</button>
        </nav>
      </header>

      <main className="hero">
        <div className="hero-content">
          <p className="hero-label">☀️ Plans are better together</p>

          <h1>
            Less group-chat chaos.
            <br />
            More good plans.
          </h1>

          <p className="hero-description">
            Create a plan, invite your people, share ideas, vote together,
            and finally decide what everyone is actually doing.
          </p>

          <div className="hero-actions">
            <button className="primary-button">Start a plan</button>

            <button className="secondary-button">
              See how it works
            </button>
          </div>
        </div>
      </main>
    </div>
  )
}

export default App