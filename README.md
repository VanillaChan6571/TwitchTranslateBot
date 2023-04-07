# Translator Twitch Bot

Translator Twitch Bot by VanillaChanny or VanillaChan6571, is a versatile, easy-to-use chatbot that allows Twitch channel viewers to translate their messages in real-time. With support for multiple languages powered by DeepL API, this bot provides a seamless translation experience for both streamers and viewers.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)

## Features
- Real-time message translation for Twitch chat
- Support for multiple languages
- Utilizes DeepL API for accurate translations
- User preferences for language settings
- Easy setup and configuration

## Prerequisites
- Java JDK 16
- DeepL API Key
- Twitch OAuth Key

## Installation
1. Clone the repository or download the source code.
2. Build the project using Maven
3. Locate the generated `TranslatorTwitchBot-1.1.jar` file in the `target` directory.

## Configuration
1. Create a `config.properties` file in the same directory as the `TranslatorTwitchBot-1.0.jar` file.
2. Populate the `config.properties` file with the required information:

twitch_username=your_twitch_username
twitch_oauth_key=your_twitch_oauth_key
deepl_api_key=your_deepl_api_key
channel=your_channel_name
target_language=EN
target_language_secondary=ES

## Usage
1. Run the bot using the following command:

java -jar TranslatorTwitchBot-1.1.jar

2. In the Twitch chat, users can set their preferred language using the `!translate` command followed by the language code:

!translate ES


Supported language codes: EN, ES

3. The bot will now translate the user's messages in real-time.

## Contributing
We welcome contributions to this project! Please feel free to create issues for bug reports or feature requests, and submit pull requests for any improvements or bug fixes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

## Acknowledgements
- [DeepL API](https://www.deepl.com/api.html) for providing the translation service
- [PircBotX](https://github.com/pircbotx/pircbotx) for the IRC framework
