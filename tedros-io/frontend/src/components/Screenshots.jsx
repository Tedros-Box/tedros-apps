import { motion } from 'framer-motion';
import Carousel from './ui/Carousel';

const screenshots = [
    './images/Screenshots/153532.png',
    './images/Screenshots/160547.png',     
    './images/Screenshots/161004.png',
    './images/Screenshots/153856.png',
    './images/Screenshots/153641.png',
    './images/Screenshots/160036.png',
    './images/Screenshots/160503.png',   
    './images/Screenshots/160913.png',    
    './images/Screenshots/160623.png',
    './images/Screenshots/160634.png',
    './images/Screenshots/160645.png',
    './images/Screenshots/160710.png'
];

const Screenshots = () => {
    return (
        <section id="screenshots" className="section-padding relative overflow-hidden">
            {/* Background Gradient */}
            <div className="absolute top-0 right-0 w-1/3 h-1/3 bg-blue-500/10 rounded-full blur-3xl -z-10" />
            <div className="absolute bottom-0 left-0 w-1/3 h-1/3 bg-violet-500/10 rounded-full blur-3xl -z-10" />

            <div className="container">
                <div className="text-center mb-16">
                    <motion.h2
                        initial={{ opacity: 0, y: 20 }}
                        whileInView={{ opacity: 1, y: 0 }}
                        transition={{ duration: 0.5 }}
                        className="text-4xl md:text-5xl font-bold mb-6"
                    >
                        Interface <span className="text-gradient">Intuitiva</span>
                    </motion.h2>
                    <motion.p
                        initial={{ opacity: 0, y: 20 }}
                        whileInView={{ opacity: 1, y: 0 }}
                        transition={{ duration: 0.5, delay: 0.1 }}
                        className="text-xl text-slate-400 max-w-2xl mx-auto"
                    >
                        Conheça o visual moderno e funcional do ecossistema Tedros Box.
                    </motion.p>
                </div>

                <motion.div
                    initial={{ opacity: 0, scale: 0.9 }}
                    whileInView={{ opacity: 1, scale: 1 }}
                    transition={{ duration: 0.7 }}
                >
                    <Carousel images={screenshots} />
                </motion.div>
            </div>
        </section>
    );
};

export default Screenshots;
