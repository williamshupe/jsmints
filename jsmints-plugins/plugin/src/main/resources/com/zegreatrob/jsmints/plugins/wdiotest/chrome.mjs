export function configure(config, directories) {
    config.services.push(
        ['chromedriver', {outputDir: directories.logs}],
    )
    config.capabilities.push({
        maxInstances: 1,
        acceptInsecureCerts: true,
        browserName: 'chrome',
        "goog:loggingPrefs": {
            "browser": "ALL"
        },
        'goog:chromeOptions': {
            'args': [
                '--no-sandbox',
                'headless',
                'show-fps-counter=true',
            ]
        },
    })
    return config
}