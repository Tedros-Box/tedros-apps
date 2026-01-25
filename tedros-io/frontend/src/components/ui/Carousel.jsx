import { useState, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { motion, AnimatePresence } from 'framer-motion';
import { ChevronLeft, ChevronRight, Maximize2, X } from 'lucide-react';

const Carousel = ({ images }) => {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [direction, setDirection] = useState(0);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        if (isModalOpen) return;
        const timer = setInterval(() => {
            nextSlide();
        }, 5000);
        return () => clearInterval(timer);
    }, [currentIndex, isModalOpen]);

    useEffect(() => {
        const handleKeyDown = (e) => {
            if (!isModalOpen) return;
            if (e.key === 'Escape') setIsModalOpen(false);
            if (e.key === 'ArrowLeft') prevSlide();
            if (e.key === 'ArrowRight') nextSlide();
        };

        window.addEventListener('keydown', handleKeyDown);
        return () => window.removeEventListener('keydown', handleKeyDown);
    }, [isModalOpen]);

    const nextSlide = () => {
        setDirection(1);
        setCurrentIndex((prev) => (prev === images.length - 1 ? 0 : prev + 1));
    };

    const prevSlide = () => {
        setDirection(-1);
        setCurrentIndex((prev) => (prev === 0 ? images.length - 1 : prev - 1));
    };

    const variants = {
        enter: (direction) => ({
            x: direction > 0 ? 1000 : -1000,
            opacity: 0,
            scale: 0.95
        }),
        center: {
            zIndex: 1,
            x: 0,
            opacity: 1,
            scale: 1
        },
        exit: (direction) => ({
            zIndex: 0,
            x: direction < 0 ? 1000 : -1000,
            opacity: 0,
            scale: 0.95
        })
    };

    return (
        <>
            <div className="relative w-full aspect-video max-w-5xl mx-auto overflow-hidden rounded-2xl shadow-2xl bg-slate-800/50 backdrop-blur border border-white/10 group">
                <AnimatePresence initial={false} custom={direction}>
                    <motion.img
                        key={currentIndex}
                        src={images[currentIndex]}
                        custom={direction}
                        variants={variants}
                        initial="enter"
                        animate="center"
                        exit="exit"
                        transition={{
                            x: { type: "spring", stiffness: 300, damping: 30 },
                            opacity: { duration: 0.2 }
                        }}
                        className="absolute w-full h-full object-contain cursor-zoom-in"
                        alt={`Screenshot ${currentIndex + 1}`}
                        onClick={() => setIsModalOpen(true)}
                    />
                </AnimatePresence>

                <button
                    onClick={() => setIsModalOpen(true)}
                    className="absolute top-4 right-4 p-2 bg-black/50 text-white rounded-full opacity-0 group-hover:opacity-100 transition-opacity z-10 hover:bg-black/70"
                    aria-label="Expand image"
                >
                    <Maximize2 size={20} />
                </button>

                <button
                    onClick={(e) => { e.stopPropagation(); prevSlide(); }}
                    className="absolute left-4 top-1/2 -translate-y-1/2 p-3 rounded-full bg-black/50 text-white hover:bg-blue-500/80 transition-colors opacity-100 md:opacity-0 md:group-hover:opacity-100 focus:opacity-100 z-10"
                    aria-label="Previous slide"
                >
                    <ChevronLeft size={24} />
                </button>

                <button
                    onClick={(e) => { e.stopPropagation(); nextSlide(); }}
                    className="absolute right-4 top-1/2 -translate-y-1/2 p-3 rounded-full bg-black/50 text-white hover:bg-blue-500/80 transition-colors opacity-100 md:opacity-0 md:group-hover:opacity-100 focus:opacity-100 z-10"
                    aria-label="Next slide"
                >
                    <ChevronRight size={24} />
                </button>

                <div className="absolute bottom-4 left-1/2 -translate-x-1/2 flex gap-2 z-10">
                    {images.map((_, idx) => (
                        <button
                            key={idx}
                            onClick={(e) => {
                                e.stopPropagation();
                                setDirection(idx > currentIndex ? 1 : -1);
                                setCurrentIndex(idx);
                            }}
                            className={`w-2.5 h-2.5 rounded-full transition-all ${idx === currentIndex ? 'bg-blue-500 w-8' : 'bg-white/50 hover:bg-white'
                                }`}
                            aria-label={`Go to slide ${idx + 1}`}
                        />
                    ))}
                </div>
            </div>

            {isModalOpen && createPortal(
                <div
                    className="fixed inset-0 z-[9999] bg-black/95 backdrop-blur-sm flex items-center justify-center p-4 md:p-8"
                    onClick={() => setIsModalOpen(false)}
                >
                    <button
                        className="absolute top-4 right-4 text-white/70 hover:text-white p-2 z-50 transition-colors"
                        onClick={() => setIsModalOpen(false)}
                    >
                        <X size={32} />
                    </button>

                    <button
                        className="absolute left-2 md:left-8 top-1/2 -translate-y-1/2 p-4 text-white/70 hover:text-white hover:bg-white/10 rounded-full transition-all z-50"
                        onClick={(e) => { e.stopPropagation(); prevSlide(); }}
                    >
                        <ChevronLeft size={32} className="md:w-12 md:h-12" />
                    </button>

                    <div className="relative w-full h-full flex items-center justify-center" onClick={(e) => e.stopPropagation()}>
                        <AnimatePresence initial={false} custom={direction}>
                            <motion.img
                                key={currentIndex}
                                src={images[currentIndex]}
                                custom={direction}
                                initial={{ opacity: 0, scale: 0.9 }}
                                animate={{ opacity: 1, scale: 1 }}
                                exit={{ opacity: 0, scale: 0.9 }}
                                transition={{ type: "spring", stiffness: 300, damping: 30 }}
                                className="max-h-full max-w-full object-contain select-none shadow-2xl rounded-lg"
                                alt={`Screenshot ${currentIndex + 1} Fullscreen`}
                            />
                        </AnimatePresence>
                    </div>

                    <button
                        className="absolute right-2 md:right-8 top-1/2 -translate-y-1/2 p-4 text-white/70 hover:text-white hover:bg-white/10 rounded-full transition-all z-50"
                        onClick={(e) => { e.stopPropagation(); nextSlide(); }}
                    >
                        <ChevronRight size={32} className="md:w-12 md:h-12" />
                    </button>
                </div>,
                document.body
            )}
        </>
    );
};

export default Carousel;
