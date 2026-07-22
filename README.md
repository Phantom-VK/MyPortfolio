![version: 0.24.0](https://img.shields.io/badge/kobweb-0.24.0-blue)
![kotlin: 2.3.10](https://img.shields.io/badge/kotlin-2.3.10-blue?logo=kotlin)
<a href="https://kobweb.varabyte.com/docs">
![User Guide docs](https://img.shields.io/badge/User_Guide-royalblue?logo=readthedocs)
</a>

# Personal Portfolio Website — 100% Kotlin

A single-page "Matrix" hacker-green themed portfolio built with [Kobweb](https://github.com/varabyte/kobweb)
(Compose HTML + Silk). Statically exported and deployed to GitHub Pages.

The repo has two modules:

- **`site/`** — the Kobweb frontend (Compose HTML/Silk). Exported to static HTML/JS/CSS via `kobwebExport`
  and served from GitHub Pages.
- **`visit-notifier/`** — a small standalone JVM backend (plain `com.sun.net.httpserver.HttpServer`, no
  framework) that receives one `POST /api/visit` per browser session and emails the owner via the
  [Resend](https://resend.com) HTTP API. Deployed separately (e.g. Render).

## Getting Started

First, run the development server by typing the following command in a terminal under the `site` folder:

```bash
$ cd site
$ kobweb run
```

Open [http://localhost:8080](http://localhost:8080) with your browser to see the result.

You can use any editor you want for the project, but we recommend using **IntelliJ IDEA Community Edition** downloaded
using the [Toolbox App](https://www.jetbrains.com/toolbox-app/).

Press `Q` in the terminal to gracefully stop the server.

### Live Reload

Feel free to edit / add / delete new components, pages, and API endpoints! When you make any changes, the site will
indicate the status of the build and automatically reload when ready.

## Exporting the Project

When you are ready to ship, you should shutdown the development server and then export the project using:

```bash
kobweb export
```

When finished, you can run a Kobweb server in production mode:

```bash
kobweb run --env prod
```

If you want to run this command in the Cloud provider of your choice, consider disabling interactive mode since nobody
is sitting around watching the console in that case anyway. To do that, use:

```bash
kobweb run --env prod --notty
```

Kobweb also supports exporting to a static layout which is compatible with static hosting providers, such as GitHub
Pages, Netlify, Firebase, any presumably all the others. You can read more about that approach here:
https://bitspittle.dev/blog/2022/staticdeploy

## Visit Notification Service

This repo now contains a separate backend service in `visit-notifier/` for visit email notifications. The frontend stays
static on GitHub Pages and sends one `POST /api/visit` request per browser session.

### Frontend config

Set the backend base URL at build time:

```bash
./gradlew :site:kobwebExport -PkobwebExportLayout=STATIC -PkobwebEnvironment=PROD -PvisitNotifyApiBaseUrl=https://your-backend.example.com
```

For local frontend development, the visit reporter falls back to `http://localhost:8787` when the site runs on
`localhost` and no explicit `visitNotifyApiBaseUrl` is provided.

### Backend config

Copy `visit-notifier/.env.example` to `visit-notifier/.env` for local development, or into your host's runtime environment
settings for deployment. Required runtime variables:

```bash
ALLOWED_ORIGINS=https://<your-gh-pages-domain>,https://<your-custom-domain>
RESEND_API_KEY=re_xxxxxxxxxxxxxxxxxxxx
VISIT_NOTIFY_TO=you@example.com
VISIT_NOTIFY_FROM=notifications@yourdomain.com
```

Emails are sent via the [Resend](https://resend.com) HTTP API (port 443), not SMTP — this is what lets the
backend run on Render's free tier, which blocks outbound SMTP port 587. `VISIT_NOTIFY_FROM` must be a
verified sender/domain in your Resend account.

Run the backend locally with:

```bash
./gradlew :visit-notifier:run
```

The backend now reads configuration from process environment variables and also from a local `.env` file. Process
environment variables take precedence over `.env` values.

Endpoints:

- `GET /health`
- `POST /api/visit`

### Render deployment

Use the Docker service type for `visit-notifier` only.

Set these values in Render:

- `Root Directory`: leave blank
- `Dockerfile Path`: `Dockerfile`
- `PORT`: `10000`
- `ALLOWED_ORIGINS`: your frontend origin(s), comma-separated
- `RESEND_API_KEY`: your Resend API key
- `VISIT_NOTIFY_TO`: the inbox that should receive the visit emails
- `VISIT_NOTIFY_FROM`: a verified sender/domain in your Resend account

After deploy, Render will give you a service URL like `https://your-service.onrender.com`. Put that in the GitHub
variable `VISIT_NOTIFY_API_BASE_URL` before you next export or deploy the frontend.
