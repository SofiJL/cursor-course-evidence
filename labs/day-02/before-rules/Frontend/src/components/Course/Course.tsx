import Image from 'next/image';
import styles from './Course.module.scss';

interface CourseProps {
  id: number;
  name: string;
  description: string | null;
  thumbnail: string | null;
}

export const Course = ({ name, description, thumbnail }: CourseProps) => {
  return (
    <article className={styles.courseCard}>
      <div className={styles.thumbnailContainer}>
        {thumbnail ? (
          <Image
            src={thumbnail}
            alt={name}
            width={400}
            height={225}
            className={styles.thumbnail}
            unoptimized
          />
        ) : (
          <div className={styles.thumbnailPlaceholder} aria-hidden="true" />
        )}
      </div>
      <div className={styles.courseInfo}>
        <h2 className={styles.courseTitle}>{name}</h2>
        {description && <p className={styles.description}>{description}</p>}
      </div>
    </article>
  );
};
