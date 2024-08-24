module.exports = {
  title: 'BetterConsole',
  description: '更好的控制台',
  head: [
    ['link', {rel: 'icon', href: '/logo/favicon.ico'}]
  ],
  themeConfig: {
    logo: '/logo/logo.png',
    nav: [
      {text: '指南', link: '/guide/'},
      {text: '进阶', link: ''},
      {
        text: '选择语言',
        items: [
          {text: '简体中文', link: ''},
          {text: 'English', link: ''}
        ]
      },
      {text: 'GitHub', link: 'https://github.com/wifi504/better-console'},
    ],
    sidebar: [
      {
        title: '开 始',
        path: '/guide/',
      },
      {
        title: '基 础',
        path: '/guide/base',
      },
      {
        title: '深入组件',
        path: '/guide/components',
      },
      {
        title: '路 由',
        path: '/guide/routers',
      },
      {
        title: '渲染机制',
        path: '/guide/renders',
      }
    ],
    smoothScroll: true,
    sidebarDepth: 3
  },
  plugins: {
    '@vuepress/medium-zoom': {},
    '@vuepress/nprogress': {},
    '@vuepress/back-to-top': {},
    '@vuepress/active-header-links': {
      sidebarLinkSelector: '.sidebar-link',
      headerAnchorSelector: '.header-anchor'
    }
  }
}