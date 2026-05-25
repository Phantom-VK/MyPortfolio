[![version: 0.23.3](https://img.shields.io/badge/kobweb-0.23.3-blue)](COMPATIBILITY.md)
[![kotlin: 2.2.20](https://img.shields.io/badge/kotlin-2.2.20-blue?logo=kotlin)](COMPATIBILITY.md)
<a href="https://kobweb.varabyte.com/docs">
![User Guide docs](https://img.shields.io/badge/User_Guide-royalblue?logo=readthedocs)
</a>
[![Varabyte Discord](https://img.shields.io/discord/886036660767305799.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/5NZ2GKV5Cs)


# Personal Portfolio Website made with 100% Kotlin

# K🕸️bweb


This is a [Kobweb](https://github.com/varabyte/kobweb) project bootstrapped with the `app/empty` template.

This template is useful if you already know what you're doing and just want a clean slate. By default, it
just creates a blank home page (which prints to the console so you can confirm it's working)

If you are still learning, consider instantiating the `app` template (or one of the examples) to see actual,
working projects.

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
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USERNAME=your-gmail-address@gmail.com
SMTP_APP_PASSWORD=your-app-password
VISIT_NOTIFY_TO=you@example.com
VISIT_NOTIFY_FROM=your-gmail-address@gmail.com
```

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
- `SMTP_HOST`: `smtp.gmail.com`
- `SMTP_PORT`: `587`
- `SMTP_USERNAME`: your Gmail address
- `SMTP_APP_PASSWORD`: your Gmail app password
- `VISIT_NOTIFY_TO`: the inbox that should receive the visit emails
- `VISIT_NOTIFY_FROM`: usually the same as `SMTP_USERNAME`

After deploy, Render will give you a service URL like `https://your-service.onrender.com`. Put that in the GitHub
variable `VISIT_NOTIFY_API_BASE_URL` before you next export or deploy the frontend.
