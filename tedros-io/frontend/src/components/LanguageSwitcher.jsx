import { useTranslation } from 'react-i18next';
import { motion } from 'framer-motion';

const LanguageSwitcher = () => {
    const { i18n } = useTranslation();

    const changeLanguage = (lng) => {
        i18n.changeLanguage(lng);
    };

    const currentLang = i18n.language || 'pt'; // Default fallback

    return (
        <div className="flex items-center gap-1 bg-slate-800/50 rounded-lg p-1 border border-white/5">
            <button
                onClick={() => changeLanguage('pt')}
                className={`px-2 py-1 rounded-md text-sm transition-all ${currentLang.startsWith('pt')
                        ? 'bg-blue-600 text-white shadow-lg shadow-blue-500/20'
                        : 'text-slate-400 hover:text-white'
                    }`}
            >
                PT
            </button>
            <button
                onClick={() => changeLanguage('en')}
                className={`px-2 py-1 rounded-md text-sm transition-all ${currentLang.startsWith('en')
                        ? 'bg-blue-600 text-white shadow-lg shadow-blue-500/20'
                        : 'text-slate-400 hover:text-white'
                    }`}
            >
                EN
            </button>
        </div>
    );
};

export default LanguageSwitcher;
